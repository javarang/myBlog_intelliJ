<%@ page contentType="text/html; charset=utf-8" import="java.util.*" %>
<html>

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
<title>화면API</title>
<meta name="description" content="">
<meta property="og:title" content="">
<meta property="og:url" content="">
<meta property="og:description" content="">
<meta property="og:image" content="">
<link href="/rss/css/layout.css?20191211" rel="stylesheet">
<script src="/rss/js/xkeypad_config.js?20191211"></script>
<script src="/rss/js/xkeypad_web.js"></script>
</head>
<script>
	function acnutTransfrButton() {
		viewKeypad(gXKModule, document.getElementById('userpasswd'));
		//document.getElementById("keypadTop").style.display = "block";
	}

	function transfrCanclButton() {
		login(false);
	}

	function addKeypadTop() {
		var xkPadTop = document.getElementById('keypadTop');
		//xkPadTop.style.display = "block";
		xkPadTop.classList.add("on");
	}

    // 로딩 이미지 생성
    function createLoading(msg) {
      var newDiv = document.createElement("div");
      newDiv.id = "overlay";
      newDiv.classList.add("overlay");
      newDiv.innerHTML = "<div class='loading'><span class='img'></span><p class='message'><b>" + msg +
        "</b></p></div>"
      document.querySelector("body").appendChild(newDiv);
    }

    // 로딩 이미지 삭제
    function removeLoading() {
      var elem = document.getElementById("overlay");
      elem.parentNode.removeChild(elem);
    }

    // 화면 높이값 초기화
    function heightInit() {
	  var wh = window.screen.height;
	  var body = document.getElementsByTagName("body")[0];
	  var cont = document.getElementById("container");
	  var btnArea = document.querySelector(".btn_area");

      if(body.offsetHeight < cont.offsetHeight) {
	    btnArea.style.position = "absolute";
      }
      else {
      	btnArea.style.position = "fixed";
      }
    }
     
    // 화면 리사이즈 시 heightInit() 호출
    window.addEventListener("resize", function(){
	   heightInit();
    }, true);

    
</script>
<body>
  <!-- 화면API -->
  <div id="acnutTransfrPage">
    <div id="container" class="transfer">

      <!-- head -->
      <div class="head">
        <h2 class="title">
          <strong>${dpName}</strong> 에게<br>
          이체하시겠습니까?
        </h2>
      </div>
      <!-- //head -->

      <!-- body -->
      <div class="body tbl-wrap">
        <table class="tbl">
          <caption>
            <strong>계좌이체 상세 내용</strong>
            <p>받는계좌, 보낸금액, 수수료, 받는분 통장 표시, 내통장 표시로 구성됨</p>
          </caption>
          <colgroup>
            <col style="width:40%">
            <col style="width:auto">
          </colgroup>
          <tbody>
            <tr>
              <th>출금계좌</th>
              <td>국민 ${wdAccount}</td>
            </tr>
            <tr>
              <th>보낼금액</th>
              <td><strong>${transferPrice}원</strong></td>
            </tr>
            <tr class="user_input hr">
              <td colspan="2">
              <label>받는분 통장 표시</label>
              <input type="text" id="sumry" name="sumry" placeholder="받는분 통장표시 10자" maxlength="10" value="${wdName}"></td>
            </tr>
            <tr class="user_input">
              <td colspan="2">
              <label>내 통장 표시</label>
              <input type="text" id="drMemo" name="drMemo" placeholder="내 통장표시 14자" maxlength="14" value="${dpName}"></td>
            </tr>
            <!-- 
            <tr>
              <th>받는계좌</th>
              <td>국민 ${dpAccount}</td>
            </tr>
             -->
          </tbody>
        </table>
      </div>
      <div class="btn_area">
        <button type="button" class="col2 btn_cancel" onclick="transfrCanclButton()">취소</button>
        <button type="button" class="col2 btn_submit" onclick="acnutTransfrButton()">확인</button>
      </div>
      <div class="overlay type1">
        <div class="ui modal">
          <i class="icon"></i>
          <div class="content">
            <div class="description">
              <p>진행중인 이체를 정말 취소하시겠습니까?<br>입력하신 정보는 초기화됩니다.</p>
            </div>
          </div>
          <div class="btn_area">
            <button type="button" class="col2 btn_cancel" onclick="canclButton()">취소</button>
            <button type="button" class="col2 btn_submit" onclick="canclButton()">확인</button>
          </div>
        </div>
      </div>
      <div class="overlay type2">
        <div class="ui modal">
          <i class="icon"></i>
          <div class="content">
            <div class="description">
              <p>비밀번호가 일치하지 않습니다.<br>다시 입력해주세요<br>
                (4회중 1회 오류)</p>
            </div>
          </div>
          <div class="btn_area">
            <button type="button" class="btn_cancel" onclick="canclButton()">확인</button>
          </div>
        </div>
      </div>
    </div>
    <!-- //body -->

  </div>
</body>

<form class="form" id="xkfform" name="xkf" method="post" action="/screenapi/local/preTransferCall" style="display:none">
	<div>
		<label for="pass">Password</label>
		<input type="hidden" title="password" readonly="true" name="userpasswd" id="userpasswd" onClick="viewKeypad(gXKModule, this);">  
		<input type="hidden" name="xksessionid" id="xksessionid" />
		<input type="hidden" name="xksectoken" id="xksectoken" />
		<input type="hidden" name="xkindexed" id="xkindexed" />
		<input type="hidden" name="hidsumry" id="hidsumry" />
		<input type="hidden" name="hiddrMemo" id="hiddrMemo" />
		<input type="hidden" name="tid" id="tid" value="${tid}" />
		<input type="hidden" name="uniKey" id="uniKey" value="${uniKey}" />
		<input type="hidden" id="width" name="width">
	</div>
</form>
  <!-- 키패드 상단 -->
  <div class="keypad-top" id="keypadTop">
    <button type="button" class="btn_close_popup" onclick="closeKeypad(gXKModule);">창 닫기</button>
    <span class="logo"></span>
    <span class="title">비밀번호</span>
    <div id="pwImage" data-length="">
      <span class="pw1"></span>
      <span class="pw2"></span>
      <span class="pw3"></span>
      <span class="pw4"></span>
    </div>.

  </div>
  <!-- //키패드 상단 -->
</html>