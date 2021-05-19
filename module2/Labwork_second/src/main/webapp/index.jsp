<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <title>News agency</title>
</head>
<body>
<h2>News agency</h2>
<div class="categories-form" style="display: flex">
    <div>
        <h2>Add news category</h2>
        <form method="post" action="${pageContext.request.contextPath}/categories">
            <div class="form-newsCategory">
                <label for="categoryName" class="control-label">Category: </label>
                <input id="categoryName" class="form-control" type="text"
                       name="categoryName"/>
            </div>
            <div class="form-newsCategory">
                <label for="ageLimit" class="control-label">Category age limit: </label>
                <input id="ageLimit" class="form-control" type="text"
                       name="ageLimit"/>
            </div>
            <div class="form-newsCategory">
                <button id="button" type="submit" class="btn btn-success">Submit</button>
            </div>
        </form>
    </div>
    <div style="margin-left: 20px">
        <h2>Update news category</h2>
        <form method="post" action="${pageContext.request.contextPath}/update?entity=newsCategory">
            <div class="form-newsCategory">
                <label for="updateCategoryId" class="control-label">Id: </label>
                <input id="updateCategoryId" class="form-control" type="number"
                       name="categoryId"/>
            </div>
            <div class="form-newsCategory">
                <label for="updatedCategoryName" class="control-label">Category: </label>
                <input id="updatedCategoryName" class="form-control" type="text"
                       name="updatedCategoryName"/>
            </div>
            <div class="form-newsCategory">
                <label for="updatedCategoryAgeLimit" class="control-label">Category age limit: </label>
                <input id="updatedCategoryAgeLimit" class="form-control" type="text"
                       name="updatedCategoryAgeLimit"/>
            </div>
            <div class="form-newsCategory">
                <button id="updateButton" type="submit" class="btn btn-success">Submit</button>
            </div>
        </form>
    </div>
</div>
<div class="news-form" style="display: flex">
    <div>
        <h2>Add news</h2>
        <form method="post" action="${pageContext.request.contextPath}/news">
            <div class="form-newsCategory">
                <label for="title" class="control-label">Title: </label>
                <input id="title" class="form-control" type="text"
                       name="title"/>
            </div>
            <div class="form-newsCategory">
                <label for="text" class="control-label">Text: </label>
                <input id="text" class="form-control" type="text"
                       name="text"/>
            </div>
            <div class="form-newsCategory">
                <label for="categoryId" class="control-label">Category id: </label>
                <input id="categoryId" class="form-control" type="text"
                       name="categoryId"/>
            </div>
            <div class="form-newsCategory">
                <button id="buttonNews" type="submit" class="btn btn-success">Submit</button>
            </div>
        </form>
    </div>
    <div style="margin-left: 20px">
        <h2>Update news</h2>
        <form method="post" action="${pageContext.request.contextPath}/update?entity=news">
            <div class="form-newsCategory">
                <label for="newsId" class="control-label">Id: </label>
                <input id="newsId" class="form-control" type="text"
                       name="newsId"/>
            </div>
            <div class="form-newsCategory">
                <label for="updatedTitle" class="control-label">Title: </label>
                <input id="updatedTitle" class="form-control" type="text"
                       name="updatedTitle"/>
            </div>
            <div class="form-newsCategory">
                <label for="updatedText" class="control-label">Text: </label>
                <input id="updatedText" class="form-control" type="text"
                       name="updatedText"/>
            </div>
            <div class="form-newsCategory">
                <label for="updatedCategoryId" class="control-label">Category id: </label>
                <input id="updatedCategoryId" class="form-control" type="text"
                       name="updatedCategoryId"/>
            </div>
            <div class="form-newsCategory">
                <button id="buttonUpdateNews" type="submit" class="btn btn-success">Submit</button>
            </div>
        </form>
    </div>
</div>
<div>
    <a href="${pageContext.request.contextPath}/news">Get all news</a>
    <a href="${pageContext.request.contextPath}/categories">Get all categories</a>
</div>
<div class="categories">
    <h2><c:out value="${categoriesTitle}"/></h2>
    <c:forEach items="${categories}" var="newsCategory" varStatus="loop">
        <div>
            <h4><c:out value="${loop.index + 1}"/></h4>
            <p>Id: <c:out value="${newsCategory.getId()}"/></p>
            <p>Name: <c:out value="${newsCategory.getName()}"/></p>
            <p>Course: <c:out value="${newsCategory.getAgeLimit()}"/></p>
            <form method="post" action="${pageContext.request.contextPath}/delete?categoryId=${newsCategory.getId()}">
                <input type="submit" value="delete">
            </form>
        </div>
    </c:forEach>
</div>
<div class="news">
    <h2><c:out value="${newsTitle}"/></h2>
    <c:forEach items="${news}" var="news" varStatus="loop">
        <div>
            <h4><c:out value="${loop.index +1 }"/></h4>
            <p>Id: <c:out value="${news.getId()}"/></p>
            <p>First name: <c:out value="${news.getTitle()}"/></p>
            <p>Last name: <c:out value="${news.getText()}"/></p>
            <p>Category id: <c:out value="${news.getCategoryId()}"/></p>
            <form method="post" action="${pageContext.request.contextPath}/delete?newsId=${news.getId()}">
                <input type="submit" value="delete">
            </form>
        </div>
    </c:forEach>
</div>
</body>
</html>
