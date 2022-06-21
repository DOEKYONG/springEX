boardlist()

function boardlist() {
$.ajax({
    url : "/board/getlist" ,
    method : "GET",
    success : function(re) {

    //console.log(re)
        let html ="";
        for(let i=0; i<re.length; i++) {

        html +=
               '<tr>'+
                     '<td><a href="/board/boardview?bno='+re[i].bno+'">'+re[i].bno+'</a></td>'+
                     '<td onclick="boardview('+re[i].bno+')">'+re[i].btitle+'</td>'+
                     '<td>'+re[i].bid+'</td>'+
                 '</tr>'
        }
        $("#bt").html(html);

    }
})
}

function boardview(bno) {

    location.href="/board/boardview?bno="+bno
    alert(bno)

     $.ajax({
              url : "/board/getboardview",
                data : {"bno":bno},
                method : "GET",
                success: function(re) {
                console.log("gg")

                }
                })

}





