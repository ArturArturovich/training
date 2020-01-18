class TableSorter {
    constructor(tbody, col, dir) {
        this.tbody = tbody;
        this.rows = tbody.rows;
        this.col = col;
        this.dir = dir;
    }

    exchange(i, j) {
        let that = this, tbody = that.tbody, trs = that.rows, tmpNode;

        if (i === j + 1) {
            tbody.insertBefore(trs[i], trs[j]);
        } else if (j === i + 1) {
            tbody.insertBefore(trs[j], trs[i]);
        } else {
            tmpNode = tbody.replaceChild(trs[i], trs[j]);
            if (!trs[i]) {
                tbody.appendChild(tmpNode);
            } else {
                tbody.insertBefore(tmpNode, trs[i]);
            }
        }
    }

    get(r) {
        let c = this.rows[r].cells[this.col];
        let v = (c.dataset.hasOwnProperty("value")) ? c.dataset.value : c.innerText.toLowerCase();
        return !isNaN(v) ? parseFloat(v) : v;
    }

    quicksort(lo, hi) {
        let i, j, pivot, that = this;

        if (hi <= lo + 1) {
            return;
        }

        if ((hi - lo) === 2) {
            if (this.dir ? (that.get(hi - 1) > that.get(lo)) : (that.get(hi - 1) < that.get(lo))) {
                that.exchange(hi - 1, lo);
            }
            return;
        }

        i = lo + 1;
        j = hi - 1;

        if (this.dir ? (that.get(lo) > that.get(i)) : (that.get(lo) < that.get(i))) {
            that.exchange(i, lo);
        }
        if (this.dir ? (that.get(j) > that.get(lo)) : (that.get(j) < that.get(lo))) {
            that.exchange(lo, j);
        }
        if (this.dir ? (that.get(lo) > that.get(i)) : (that.get(lo) < that.get(i))) {
            that.exchange(i, lo);
        }

        pivot = that.get(lo);

        while (true) {
            j--;
            while (this.dir ? (pivot > that.get(j)) : (pivot < that.get(j))) {
                j--;
            }
            i++;
            while (this.dir ? (that.get(i) > pivot) : (that.get(i) < pivot)) {
                i++;
            }
            if (j <= i) {
                break;
            }
            that.exchange(i, j);
        }
        that.exchange(lo, j);

        if ((j - lo) < (hi - j)) {
            that.quicksort(lo, j);
            that.quicksort(j + 1, hi);
        } else {
            that.quicksort(j + 1, hi);
            that.quicksort(lo, j);
        }
    }
}

document.addEventListener("DOMContentLoaded", function () {
    $(".table-sortable").each((k, t) => {
        let sort = null;
        let $sort = null;
        let dir = 0;
        let index = 0;
        $("thead th", t).each((i, it) => {
            it.innerHTML += `<i class="fa sort-handler fa-sort"></i>`;
            it.addEventListener("click", e => {
                let target = e.target.closest("th");
                if (target !== sort && sort !== null) {
                    $sort.removeClass("fa-sort-up").removeClass("fa-sort-down").addClass("fa-sort");
                }
                sort = target;
                $sort = $(".sort-handler", target);
                index = (Array.prototype.indexOf.call(target.parentNode.childNodes, target) - 1) / 2;
                if ($sort.hasClass("fa-sort") || $sort.hasClass("fa-sort-up")) {
                    $sort.removeClass("fa-sort").removeClass("fa-sort-up").addClass("fa-sort-down");
                    dir = 1;
                } else if ($sort.hasClass("fa-sort-down")) {
                    $sort.removeClass("fa-sort-down").addClass("fa-sort-up");
                    dir = 2;
                }
                let $table = $(e.target).closest("table");
                let parent = $table.parent();
                $table.detach();
                $("tbody", t).each((bk, tb) => {
                    let ts = new TableSorter(tb, index, dir === 2);
                    ts.quicksort(0, tb.rows.length)
                });
                $table.appendTo(parent);
            });
        });
    });
});