///////////////페이지가 처음 열을때 게시물 출력 메소드 호출//////////////////////////
board_list(  9 , 0  , "" , "" );       //  cno , page , key , keyword
category_list( );
///////////////////////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////   전역변수  : 페이징이 변화가 있어도 검색 내용 유지  ///////////////////////////////////////////////////////
let current_cno = 9; // 카테고리 선택 변수 [ 없을경우 1 = 자유게시판 ]
let current_page = 0;
let current_key = ""; // 현재 검색된 키 변수  [ 없을경우 공백 ]
let current_keyword = ""; // 현재 검색된 키워드 변수[ 없을경우 공백 ]
function board_list(cno , page , key , keyword) {

this.current_cno = cno ;
        this.current_page = page;
        if( key != undefined ) { this.current_key = key; }
        if( keyword != undefined ){ this.current_keyword = keyword; }
$.ajax({
    url : "/board/getlist" ,
      data : {"cno" :  this.current_cno , "key" :  this.current_key  , "keyword" : this.current_keyword , "page" :  this.current_page } ,
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







