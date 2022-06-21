getboard(bno);
console.log(bno)
function getboard(bno) {
    $.ajax({
    url : "/board/getboardview",
    data : {"bno",bno},
    success: function(re) {
    alert("dd")
    }
    })
}
