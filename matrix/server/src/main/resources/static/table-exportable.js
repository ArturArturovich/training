const rxIsInt = /^\d+$/,
    rxIsFloat = /^\d*\.\d+$|^\d+\.\d*$/,
    // If a string has leading or trailing space,
    // contains a comma double quote or a newline
    // it needs to be quoted in CSV output
    rxNeedsQuoting = /^\s|\s$|,|"|\n/,

    isNumber = o => Object.prototype.toString.apply(o) === '[object Number]',

    isString = o => Object.prototype.toString.apply(o) === '[object String]',

    prepField = function (field) {
        if (isString(field)) {
            // Escape any " with double " ("")
            field = field.replace(/"/g, '""');

            // If the field starts or ends with whitespace, contains " or , or a newline or
            // is a string representing a number, quote it.
            if (rxNeedsQuoting.test(field) || rxIsInt.test(field) || rxIsFloat.test(field)) {
                field = '"' + field + '"';
                // quote empty strings
            } else if (field === "") {
                field = '""';
            }
        } else if (isNumber(field)) {
            field = field.toString();
        } else if (field === null || field === undefined) {
            field = '';
        } else {
            field = field.toString();
        }
        return field;
    },

    listOf = (el, selector) => Array.from(el.querySelectorAll(selector)),

    htmlOf = list => list.map(v => v.innerText);

/**
 Converts an array into a Comma Separated Values string.
 Each item in the array should be an array that represents one row in the CSV.
 Nulls and undefined values are interpreted as empty fields.
 @method arrayToCsv
 @param {Array} a The array to convert
 @returns {String} A CSV representation of the provided array.
 @for CSV
 @public
 @static
 @example
 var csv,
 books = [
 ['JavaScript: The Good Parts', 'Crockford, Douglas', 2008],
 ['Object-Oriented JavaScript', 'Stefanov, Stoyan', 2008],
 ['Effective JavaScript', 'Herman, David', 2012]
 ];
 csv = CSV.arrayToCsv(books);
 // csv now contains:
 //
 // JavaScript: The Good Parts,"Crockford, Douglas",2008\n
 // Object-Oriented JavaScript,"Stefanov, Stoyan",2008\n
 // Effective JavaScript,"Herman, David",2012\n
 */
function toCsv(a) {
    let out = '';
    for (let i = 0; i < a.length; i += 1) {
        let row = a[i];
        for (let j = 0; j < row.length; j += 1) {
            let cur = prepField(row[j]);
            out += j < row.length - 1 ? cur + ',' : cur;
        }
        // End record
        out += "\n";
    }
    return out;
}

document.addEventListener("DOMContentLoaded", function () {

    const saveData = (function () {
        let a = document.createElement("a");
        document.body.appendChild(a);
        a.style.display = "none";
        return function (blob, fileName) {
            let url = window.URL.createObjectURL(blob);
            a.href = url;
            a.download = fileName;
            a.click();
            window.URL.revokeObjectURL(url);
        };
    }());

    $(".table-exportable").each((k, t) => {
        let $btnCsv = $(`<button class="btn btn-sm btn-primary float-right btn-csv" title="Export"><i aria-hidden="true" class="fa fa-file-excel-o"></i></button>`);
        $btnCsv.click(() => {
            let csv = toCsv([
                htmlOf(listOf(document, "thead th")),
                ...listOf(t, "tbody tr:not(.hidden)").map(v => htmlOf(listOf(v, "td")))
            ]);
            console.log(csv);
            saveData(new Blob([csv], {type: 'text/csv'}), "archive.csv");
        });
        $btnCsv.insertBefore(t);
    });
});