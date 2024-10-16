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

<div style="display: flex; justify-content: space-between; padding:  3rem 4rem 10rem 5rem ">
        <a href="views/createForm.jsp" class="button-50">Create new employee</a>
        <c:choose>
        <c:when test="${empty employeeList}">
            <p>No employees found.</p>

        </c:when>
        <c:otherwise>
        <form action="http://localhost:8000/EmployManger/search" method="GET">
            <input class="button-50" type="search" name="search">
            <button class="button-50" type="submit">search</button>
        </form>
        <form action="http://localhost:8000/EmployManger/filter" method="GET">

            <select class="button-50" name="filter">
                <option value="RIPD">RIPD</option>
                <option value="IT">IT</option>
                <option value="TECH">TECH</option>
                <option value="INV">INV</option>
                <option value="TEST">TEST</option>
                <option value="PROD">PROD</option>
                <option value="DEV">DEV</option>
                <option value="ARCH">ARCH</option>
            </select>
            <button class="button-50" type="submit">filter</button>
        </form>

</div>
        <div style=" padding:20px; display: flex; flex-wrap: wrap;  gap:50px 20px;">
        <c:forEach var="employee" items="${employeeList}">
            <div class="card">
            <header class="card-header">
                <div class="logo">
                    <img src="static/logo.png" alt="Arstotzka Logo">
                </div>
                <h1>A R S T O T Z K A</h1>
                <div class="visa-status"></div>
            </header>



            <section class="photo-section">
                <div class="photo">
                    <img src="static/${employee.picture}" alt="Face Photo" style="height: 9.5rem; width:10rem;">
                </div>
                <div class="visa-status-container">
                    <div class="visa-status">
                        <div class="granted">
                            <span>Granted</span>

                            <div class="boxes">
                                <div class="box"></div>
                                <div class="box"></div>
                                <div class="box yellow"></div>
                                <div class="box yellow"></div>
                            </div>

                        </div>
                        <hr width="100%;" color="#f5b5b5" size="3">
                        <div class="entry-visa-status">ENTRY EMPLOY STATUS</div>
                        <div class="dots-grid">

                            <div class="circle filled"></div>
                            <div class="circle filled"></div>
                            <div class="circle filled"></div>
                            <div class="circle filled"></div>
                            <div class="circle"></div>
                            <div class="circle filled"></div>
                            <div class="circle filled"></div>

                            <!-- Row 2 -->
                            <div class="circle"></div>
                            <div class="circle filled"></div>
                            <div class="circle filled"></div>
                            <div class="circle"></div>
                            <div class="circle"></div>
                            <div class="circle filled"></div>
                            <div class="circle filled"></div>

                            <!-- Row 3 -->
                            <div class="circle filled"></div>
                            <div class="circle filled"></div>
                            <div class="circle filled"></div>
                            <div class="circle"></div>
                            <div class="circle filled"></div>
                            <div class="circle filled"></div>
                            <div class="circle filled"></div>

                            <div class="circle"></div>
                            <div class="circle filled"></div>
                            <div class="circle filled"></div>
                            <div class="circle"></div>
                            <div class="circle"></div>
                            <div class="circle filled"></div>
                            <div class="circle filled"></div>
                        </div>
                    </div>
                </div>
            </section>



            <section class="personal-info">
                <div class="info-row">
                    <p class="info left">${employee.id}</p>
                    <p class="info right">${employee.name}</p>
                </div>
                <div class="label-row">
                    <span class="label left">I.D</span>
                    <span class="label right">FIRST NAME</span>
                </div>
                <div class="info-row">
                    <p class="info left">${employee.salary}</p>
                    <p class="info right">${employee.hire_date}</p>
                </div>
                <div class="label-row">
                    <span class="label left">SALARY</span>
                    <span class="label right">HIRE DATE</span>
                </div>
                <div class="info-row">
                    <p class="info left">${employee.dob}</p>
                    <p class="info right">${employee.ssn}</p>
                </div>
                <div class="label-row">
                    <span class="label left">DOB</span>
                    <span class="label right">SSN</span>
                </div>
                <div class="info-row">
                    <p class="info left">${employee.phone}</p>
                    <p class="info right">${employee.email}</p>
                </div>
                <div class="label-row">
                    <span class="label left">PHONE NUMBER</span>
                    <span class="label right">EMAIL</span>
                </div>

                <div class="info-row">
                    <p class="info left">${employee.department}</p>
                    <p class="info right">${employee.position}</p>
                </div>
                <div class="label-row">
                    <span class="label left">DEPARTMENT</span>
                    <span class="label right">POSITION</span>
                </div>

                <div class="info-row">
                    <p class="info left">${employee.number_of_children}</p>
                    <p class="info right">${employee.vacation}</p>
                </div>
                <div class="label-row">
                    <span class="label left">CHILDREN</span>
                    <span class="label right">VACATION</span>
                </div>
            </section>




            <section class="update-delete">
                <a href="/EmployManger/update?id=${employee.id}" class="update-button">update</a>
                <a href="/EmployManger/delete?id=${employee.id}" class="delete-button">delete</a>
            </section>
        </div>
        </c:forEach>
        </div>

    </c:otherwise>
</c:choose>

</body>
</html>
