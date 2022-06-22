function boardwrite() {
        let form =$("#writeform")[0];
        let formdata = new FormData(form);

        $.ajax({
             url: "/board/boardwrite",
                    method: "POST",
                    data :  formdata,
                    contentType : false,
                    processData: false,
                    success: function( re ){
                    alert(re)
                        if(re == true) {
                           alert("게시글작성성공")
                        }
                        else {
                            alert("실패 서비스오류 ")
                        }
                    }

        })
}