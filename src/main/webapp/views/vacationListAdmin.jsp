<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>


<html>
<head>
    <title>Employee List</title>
    <link rel="stylesheet" type="text/css" href="static/css/style.css">
    <link href="https://fonts.googleapis.com/css2?family=VT323&display=swap" rel="stylesheet">

</head>

<body>
<div style="position: relative; width: 100%;">
    <img src="static/homebg.png" style="position: fixed ; z-index: -3; width: 100%;" alt="Arstotzka Logo">
    <div style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0, 0, 0, 0.3); z-index: -3;"></div>
</div>

<div style=" padding:20px; display: flex; flex-wrap: wrap;  gap:50px 20px;">
    <c:forEach var="vacation" items="${vacationList}">
        <div class="card">
            <header class="card-header">
                <div class="logo">
                    <img src="static/logo.png" alt="Arstotzka Logo">
                </div>
                <h1>A R S T O T Z K A</h1>
                <div class="visa-status"></div>
            </header>

            <div class="photo">
                <img src="static/${vacation.justificationDocument}" alt="Document Photo" style=" height: 9.5rem; width:10rem;">
            </div>

            <section class="personal-info">
                <div class="info-row">
                    <p class="info left">${vacation.startDate}</p>
                    <p class="info right">${vacation.endDate}</p>
                </div>
                <div class="label-row">
                    <span class="label left">START DATE</span>
                    <span class="label right">END DATE</span>
                </div>
                <div class="info-row">
                    <p class="info left">${vacation.reason}</p>
                    <p class="info right">${vacation.status}</p>
                </div>
                <div class="label-row">
                    <span class="label left">REASON</span>
                    <span class="label right">STATUS</span>
                </div>
                <div class="info-row">
                    <p class="info left">${vacation.submittedDate}</p>
                    <p class="info right">${vacation.employee.name}</p>
                </div>
                <div class="label-row">
                    <span class="label left">SUBMITED DATE</span>

                    <span class="label right">Employee</span>
                </div>
            </section>
            <section class="update-delete">
                <a href="/EmployManger/Approve?id=${vacation.id}" class="update-button">Approve</a>
                <a href="/EmployManger/Reject?id=${vacation.id}" class="delete-button">Reject</a>
            </section>

        </div>
    </c:forEach>
</div>

</body>
</html>
