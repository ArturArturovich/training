function initFilter(table) {
    let rows = table.querySelectorAll("tbody tr");
    let ths = Array.from(table.querySelectorAll("thead tr>th"));
    let filter = function () {
        rows.forEach(row => {
            let cells = Array.from(row.querySelectorAll("td"));
            row.classList.toggle("hidden", filters.some(function (it, k) {
                if (!!it.value) {
                    return !cells[k].innerText.toLowerCase().includes(it.value.toLowerCase());
                } else {
                    return false;
                }
            }));
        });
    };
    let filterRow = document.createElement("tr");
    let filters = ths.map(() => {
        let filterHeader = document.createElement("th");
        filterHeader.style.cssText = 'max-width:0px';
        filterHeader.innerHTML = '<input class="form-control"/>';
        let input = filterHeader.querySelector("input");
        input.addEventListener("keyup", filter);
        filterRow.appendChild(filterHeader);
        return input;
    });
    table.querySelector("thead").appendChild(filterRow);
}

document.addEventListener("DOMContentLoaded", function () {
    Array.from(document.querySelectorAll(".table-filtered")).forEach(initFilter);
});