<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sloth Comics</title>
    <link rel="shortcut icon" href="img/favicon.ico" type="image/x-icon">
    <link rel="icon" href="img/favicon.ico" type="image/x-icon">
    <link rel="stylesheet" type="text/css" href="index.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    <c:url value="/index.css" var="styleUrl"/>
    <c:url value="/index.js" var="indexScriptUrl"/>
    <c:url value="/slider.js" var="sliderScriptUrl"/>
    <c:url value="/back-to-menu.js" var="backScriptUrl"/>
    <c:url value="/login.js" var="loginScriptUrl"/>
    <c:url value="/login-menu.js" var="lMenuShowScriptUrl"/>
    <c:url value="/register-menu.js" var="rMenuShowScriptUrl"/>
    <c:url value="/user.js" var="userScriptUrl"/>
    <c:url value="/items.js" var="itemsScriptUrl"/>
    <c:url value="/item.js" var="itemScriptUrl"/>
    <c:url value="/checkout.js" var="checkoutScriptUrl"/>
    <c:url value="/logout.js" var="logoutScriptUrl"/>
    <c:url value="/register.js" var="registerScriptUrl"/>
    <script src="${indexScriptUrl}"></script>
    <script src="${sliderScriptUrl}"></script>
    <script src="${backScriptUrl}"></script>
    <script src="${loginScriptUrl}"></script>
    <script src="${lMenuShowScriptUrl}"></script>
    <script src="${rMenuShowScriptUrl}"></script>
    <script src="${userScriptUrl}"></script>
    <script src="${itemsScriptUrl}"></script>
    <script src="${itemScriptUrl}"></script>
    <script src="${checkoutScriptUrl}"></script>
    <script src="${logoutScriptUrl}"></script>
    <script src="${registerScriptUrl}"></script>
    <link rel="stylesheet" type="text/css" href="${styleUrl}">
</head>

<body>

    <div id="login-register-container" class="content">
        <div class="login-register-form"></div>
        <div class="login-register-form form-cover">
            <div id="login-register-header-button">
                <button id="login" onclick="onLoginMenuButtonClicked()">Login</button>
                <button id="register" onclick="onRegisterMenuButtonClicked()">Register</button>
            </div><hr>
            <h1>Sloth Comics</h1><hr>
            <div id="login-body" class="content">
                <i class="fa fa-book" style="font-size:48px;color: rgb(241, 152, 42)"></i><br><br>
                <form id="login-form" onsubmit="return false;">
                    <input type="text" placeholder="E-mail" name="email"><hr>
                    <input type="password" placeholder="Password" name="password"><hr>
                    <button id="login-button" class="login-register-button"><h2>Enter</h2></button>
                </form>
            </div>
            <div id="register-body" class="hidden content">
                <form onsubmit="return false;" id="register-form" >
                    <input type="text" placeholder="Username" id="name">
                    <input type="password" placeholder="Password" id="password">
                    <input type="text" placeholder="Email" id="email">
                    <input type="text" placeholder="Country" id="country">
                    <input type="text" placeholder="City" id="city">
                    <input type="text" placeholder="Street" id="street">
                    <input type="text" placeholder="Zip-code" id="zip"><hr>
                    <button id="register-button" class="login-register-button"><h2>Enter</h2></button><hr>
                </form>
            </div>
        </div>
     </div>

    <header id="site-header" class="site-header hidden content" >
        <div id="header-inner">
            <div id="site-header__main">
                <a href="javascript:void(0)" class="site-title"><img src="img/slothLogo.png" alt=""></a>
            </div>
            <div class="site-header__connect">
                <nav>
                    <ul id="nav-id">
                        <li id="nav-user-button" class="nav-li-el">User Info</li>
                        <li id="nav-shop-button" class="nav-li-el">Shop</li>
                        <li id="nav-checkout-button" class="nav-li-el">Checkout</li>
<%--                        <li id="nav-add-button" class="nav-li-el">Upload</li>--%>
                        <li id="nav-logout-button" class="nav-li-el">Logout</li>
                    </ul>
                </nav>
            </div>
            <div><img src="img/boom.png" alt=""></div>
        </div>
    </header>

    <div id="carousel" class="carousel hidden content">
        <img class="mySlides" src="https://s3.amazonaws.com/media-us-standrad/wp-content/uploads/2019/07/30185335/SALE_PowerWomen_FarewellMyDearCramer-KCcom_1500x500.jpg">
        <img class="mySlides" src="https://s3.amazonaws.com/media-us-standrad/wp-content/uploads/2019/07/15220533/SDCC_InitalD_Pre-KCcom_1500x500.jpg">
        <img class="mySlides" src=" https://s3.amazonaws.com/media-us-standrad/wp-content/uploads/2019/07/24184131/SALE_BingeReading-KCcom_1500x500.png">
    </div>

     <div id="user-info-body" class="hidden content">
        <h2 id="ui-h2">User Info</h2>
         <form id="user-form" onsubmit="return false">
            <b>Name: </b><input type="text" name="name">
            <b>E-mail: </b><input type="text" name="email">
            <b>Country: </b><input type="text" name="country">
            <b>City: </b><input type="text"  name="city">
            <b>Street: </b> <input type="text" name="street">
            <b>Zip-code: </b><input type="text" name="zip">
            <b>Money: </b><input type="text" name="money">
            <button id="update-user"><h2>Update</h2></button>
         </form>
    </div>

    <div id="shop-body" class="hidden content">
    </div>

    <div id="item-view-body" class="item-view-body hidden content">
    </div>

    <div id="checkout-body" class="checkout-body hidden content">
    </div>

    <div id="admin-add" class="hidden content">
        <h2>Add New Book</h2>
        <input type="text" placeholder="Title">
        <input type="text" placeholder="Author">
        <input type="text" placeholder="URL">
        <input type="text" placeholder="Price">
        <textarea name="book" id="" cols="30" rows="10" placeholder="About..."></textarea><br>
        <button id="create-post"><h2>Create</h2></button>
     </div>

</body>

</html>
