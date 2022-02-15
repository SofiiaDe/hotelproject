<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<%@ include file="/WEB-INF/jspf/page.jspf" %>


<script>
  function changeFieldElement(element, value)
  {
    document.getElementsByName(element)[0].value = value;
  }
</script>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <a class="navbar-brand" href="/Hotel/controller?command=clientAccount">
    <img src="image/logo-hotel.jpg" width="30" height="30" class="d-inline-block align-top" alt="">
    <fmt:message key="client.menu.label.client"/>
  </a>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="controller?command=applicationPage"><fmt:message key="client.menu.button.submit.application"/> <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
              <a class="nav-link" href="controller?command=freeRoomsPage"><fmt:message key="client.menu.button.book"/></a>
      </li>
    </ul>


    ${sessionScope.authorisedUser.firstName} ${sessionScope.authorisedUser.lastName}
    <div class="dropdown">
      <button class="btn btn-outline-secondary btn-sm" type="button" id="dropdownMenuButton"
              data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
        <i class="material-icons">language </i>
      </button>
      <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
        <form class="form-inline" method="post" action="controller?command=i18n">
          <button type="submit" name="en" onclick="changeFieldElement('langField', 'en')" class="dropdown-item"><fmt:message key="main.menu.button.english"/></button>
          <button type="submit" name="uk" onclick="changeFieldElement('langField', 'uk')" class="dropdown-item"><fmt:message key="main.menu.button.ukrainian"/></button>
          <input name="langField" type="hidden" value="en">
        </form>
      </div>
    </div>
    <form class="form-inline my-2 my-lg-0" method="post" action="controller?command=logout">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit"><fmt:message key="main.menu.button.logout"/></button>
    </form>
  </div>
</nav>
