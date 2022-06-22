board_list(1)
category_list();

function board_list(cno) {
$.ajax({
    url : "/board/getlist" ,
    data : {"cno" : cno},
    method : "GET",
    success : function(re) {

    console.log(re);
        let html ="<tr> <th >번호</th> <th>제목</th> <th>작성자</th> </tr>";
        for(let i=0; i<re.length; i++) {

        html +=
               '<tr>'+
                     '<td><a href="/board/boardview/'+re[i].bno+'">'+re[i].bno+'</a></td>'+
                     '<td ">'+re[i].btitle+'</td>'+
                     '<td>'+re[i].bid+'</td>'+
                 '</tr>'
        }
        $("#bt").html(html);

    }
})
}

function category_list() {
    $.ajax({
        url : "/board/getcategorylist",
        method : "GET",
        success : function( re ){
        let html ="";
            for(let i=0; i<re.length; i++) {

                html +=
                   ' <button onclick ="board_list('+re[i].cno+')">'+re[i].cname+'</button>';
            }

            $("#categorybox").html(html)

                }
            })
        }







