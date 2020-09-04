<ul class="nav nav-pills">
    <li class="nav-item">
        <a class="nav-link" href="add-user.html"><s:message code="index.addUserLink"/></a>
    </li>
    <!--if you're not a manager, you won't this will not be rendered for you-->
    <sec:authorize access="hasRole('ADMIN')">
        <li class="nav-item">
            <a class="nav-link" href="show-users.html"><s:message code="index.showUsersLink"/></a>
        </li>
    </sec:authorize>
    <li class="nav-item">
        <a class="nav-link" href="logout.html">
            <s:message code="index.logoutLink"/>
            <!--get logged-in user's name from security context (using sec lib tags)-->
            <sec:authentication property="principal.username"/>
        </a>
    </li>
</ul>