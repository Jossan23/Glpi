# Glpi

GLPI is a management software for bussiness which is used to control all the TI infrastructure and push reports so that TI admins can see and resolve the incidents.
When I made my interships, there was some problems with this GLPI software:
  1. Not all people knew the URL used on the Intranet to create reports.
  2. TI admins needed to access the web url everytime to view all reports.

With an Android app, you have it accesible for everyone and everywhere, just open the app and log in.

<h2>How does it work?</h2>

It connects to the integrated GLPI API REST using Retrofit2 library to make CRUD operations on the API REST.

<h2>What can you do with it?:</h2>

  <hr>
  Create a new ticket.
  <hr>
  Update your current tickets.
  <hr>
  List all tickets avaliable(if the user which is login has admin rights).
  <hr>
  List your own tickets.
  <hr>
  Delete tickets(if the user have permissions).
  <hr>

<h2>In order to run the application, it is compulsory to change the URL of the GLPI API REST</h2>







