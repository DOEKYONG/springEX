function boardupdate(){

    let form = $("#updateform")[0];
    let formdata = new FormData(form);


        $.ajax({
            url : "/board/update" ,
            data : formdata ,
            method : "PUT",
             processData : false ,
             contentType : false ,
            success : function( re ){
                if(re){
                alert("수정완료")
                }
                else {
                alert("실패 비번오류")
                }
            }
        });
}