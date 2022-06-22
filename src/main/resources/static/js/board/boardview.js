getboard()

function getboard() {
    $.ajax({
    url : "/board/getboardview",

    success: function(re) {
    let html =
         '<div> 제목 :'+re.btitle+'</div>'+
         '<div> 내용 :'+re.bcontent+'</div>'+
         '<div> 작성자 :'+re.bid+'</div>'+
         '<input type="text" name="mpassword" id="mpassword">'+
         ' <button onclick="boarddelete('+re.bno+')">삭제</button>';
         $("#boardview").html(html)
    }
    })
}
function boarddelete(bno) {
let mpassword = $("#mpassword").val()
    $.ajax({
        url : "/board/delete",
                 method : "DELETE",
                  data : {"bno" : bno,"mpassword" :mpassword } ,
                 success : function( re ){
                    if(re) {
                        alert("삭제성공")
                    }
                    else {
                        alert("실패")
                    }
                 }
             });
        }

