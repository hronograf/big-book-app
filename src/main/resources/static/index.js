(function () {

    let nextPage = 0;

    $('#addBookForm').submit(function (e) {
        e.preventDefault();

        $.ajax({
            type: 'POST',
            url: '/books/create',
            dataType: 'json',
            data: JSON.stringify({
                title: $(this).find('[name=title]').val(),
                isbn: $(this).find('[name=isbn]').val(),
                author: $(this).find('[name=author]').val()
            }),
            beforeSend: function (xhr) {
                xhr.setRequestHeader('Content-Type', 'application/json')
            },
            success: function (response) {
                alert(response.message);
                updateBooksTable();
            },
            error: function (jqXHR) {
                alert(jqXHR.responseJSON.message);
            }
        });
    });

    function updateBooksTable() {
        nextPage = 0;
        searchRequest('', '', nextPage, fillBooksTable);
    }

    $('#searchForm').submit(function (e) {
        e.preventDefault();
        nextPage = 0;
        searchRequest($(this).find('[name=title]').val(), $(this).find('[name=isbn]').val(), nextPage, fillBooksTable);
    });

    $('#moreBooksButton').click(function () {
        let searchFormEl = $('#searchForm');
        searchRequest(searchFormEl.find('[name=title]').val(), searchFormEl.find('[name=isbn]').val(), nextPage, addToBooksTable);
    });

    function searchRequest(title, isbn, page, changeBookTableFunc) {
        $.ajax({
            url: '/books/search?' + $.param({
                title: title,
                isbn: isbn,
                page: page
            }),
            success: function (response) {
                changeBookTableFunc(response.data.books);
                updateUpToNumber(response.data.canBeShown);
                if (response.data.books.length > 0)
                    nextPage++;
            },
            error: function (err) {
                console.log(err);
            }
        });
    }

    function updateUpToNumber(number) {
        $('#upToNumber').html(number);
    }

    function addToBooksTable(books) {
        $("#booksTableBody").append(generateBooksHtml(books));
    }

    function fillBooksTable(books) {
        $("#booksTableBody").html(generateBooksHtml(books));
    }

    function generateBooksHtml(books) {
        let tableContent = "";
        let newLocation;
        for (let i = 0; i < books.length; ++i) {
            newLocation = '/book/' + books[i].isbn;
            tableContent += `
        <tr class="hand-hover" onclick="document.location = '${newLocation}';">
            <td>${books[i].title}</td>
            <td>${books[i].isbn}</td>
            <td>${books[i].author}</td>
        </tr>
        `;
        }
        return tableContent;
    }

    updateBooksTable();
})();
