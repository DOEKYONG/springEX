boardlist()

function boardlist() {
$.ajax({
    url : "/board/boardlist" ,
    method : "get",
    success : function(boardlist) {
        let html ="";
        for(let i=0; i<boardlist.length; i++) {
        html +=
               '<tr>'+
                     '<td>'++'</td>'+
                     '<td>내용</td>'+
                     '<td>작성자</td>'+
                 '</tr>'
        }
        $("#bt").html(html);

    }
})
}