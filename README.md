##Android Vacation Planner App

## Overview
The Vacation Planner App is a mobile application course project. Its purpose is to demonstrate Android application development using the Android SDK.

The app allows users to create, edit, and manage vacations, including details like start and end dates and excursion planning. Data is stored locally using Room (SQLite).


--

## Features

- Create, edit, alert, and delete vacations with fields such as vacation title, hotel, start, and end date.
- Associate excursions with vacations, including the title and date.
- Set notifications for vacation start/end date and excursion dates.
- Share vacation and excursion details through Android sharing options on the Vacation detail menu.
- Data validation is added to ensure an accurate date range of excursions.
- Sample data is added to the vacation list menu bar.

## Instructions to Install

1. Clone repository: git clone https://gitlab.com/wgu-gitlab-environment/student-repos/ajo5719/d424-software-engineering-capstone.git
2. Open Android Studio(Flamingo)
3. Target SDK: 36
4. Build and run the app using:
   -Android Emulator or a physical Android device.

##Guide to Use

##Access the Vacation Planner
-Launch the app and click the "enter" button on the home screen.
-This will open the Vacation List, displaying all vacations.
-Click the menu bar on the Vacation List page and select "Add Sample Code" to populate sample vacations.
-Refresh the page to view sample data.

##Vacation Details
-Add a Vacation by clicking the + button on the Vacation List page. Fill the form, click the menu bar, and click "Save Vacation".
-Edit a Vacation by clicking a vacation from the Vacation List, making changes, clicking the menu, and clicking "Save Vacation".
-Delete a Vacation by clicking a vacation from the Vacation List, clicking the menu, and clicking "Delete Vacation".
-Click "Start Alert", "End Alert", or "Alert" to set notifications on the vacation dates for the menu of the vacation detail page.
-Share details through the vacation detail page and click the menu bar, then click "Share".


##Manage Excursions
-Add an excursion by clicking the + button from the Vacation Detail page, complete the form, then click the menu bar and click "Save Excursion".
-Set Alert from the excursion detail page by clicking the menu bar and clicking "Alert".