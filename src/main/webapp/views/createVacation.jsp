<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<html>
<head>
    <title>Employees</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=VT323&display=swap" rel="stylesheet">
</head>
<body>
<div style="position: relative; width: 100%;">
    <img src="${pageContext.request.contextPath}/static/bgForm.png" style="position: fixed; z-index: -3; width: 100%" alt="Arstotzka Logo">
    <div style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.3); z-index: -3;"></div>
</div>

<div style="padding:50px ">
    <a href="http://localhost:8000/EmployManger/vacationList" class="button-50">Home</a>

    <form style="display: flex; justify-self: center; align-self: center" action="http://localhost:8000/EmployManger/createVacation" method="post" enctype="multipart/form-data">
        <div class="card">
            <header class="card-header">
                <div class="logo">
                    <img src="${pageContext.request.contextPath}/static/logo.png" alt="Arstotzka Logo">
                </div>
                <h1>A R S T O T Z K A</h1>
                <div class="visa-status"></div>
            </header>

            <div class="photo" style="position: relative">


                <input required id="file-input" name="justificationDocument" type="file" style="display: none;">


                <label for="file-input" style="cursor: pointer; position: absolute; top: 100px; left: 25px;">
                    <img src="${pageContext.request.contextPath}/static/pxu.png" style="height: 3rem; width: 3rem;">
                </label>
            </div>

            <section class="personal-info">
                <div class="info-row">
                    <p class="info left"><input required type="date" class="inputs" name="startDate" placeholder="Enter Name"></p>
                    <p class="info right"><input required type="date" class="inputs" name="endDate" placeholder="Enter Name"></p>
                </div>
                <div class="label-row">
                    <span class="label left">START DATE</span>
                    <span class="label right">END DATE</span>
                </div>
                <div class="info-row">
                    <p class="info left"><input required type="text" class="inputs" name="reason" placeholder="Social Security Number"></p>
                    </div>
                <div class="label-row">
                    <span class="label left">REASON</span>
                </div>

            </section>

            <sectionsty style="margin-left: 40px" class="update-delete">
                <button type="submit" class="update-button">Create Request</button>
            </sectionsty>
        </div>
    </form>
</div>
</body>
</html>
