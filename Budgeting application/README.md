# My Personal Project - A Budget and Expenses Tracking App

## What will the application do?
This application will keep track of any expenses, whether previous or potentially upcoming, that
the user wishes to add. The user will be able to create categories for each type of purchase, 
and this will generally allow the user to easily keep track of what they should be spending relative
to their budget. 

## Who is this application for?
This application will be available to any user that wishes to use it. It is particularly useful for
users that have trouble keeping track of all their previous and upcoming payments, and would like an 
easy way to keep track of them. 
There are many reasons for using this application, *including*:
- keeping track of subscription services
- easily demarcating between necessary expenses and non-essential ones
- plan out effective ways to improve budgeting, and track your progress

## Why do I want to make this project?
Having personally experienced having trouble keeping track of all my expenses, this is something that
I want to create as a way to showcase my ability to create programs that could solve real life issues
that I myself have personally faced. I think this project will also help me improve my skills as a 
software developer. 

### Phase 1 User Stories:
- As a user, I want to be able to create a budget list and name it
- As a user, I want to be able to add an expense to my budget list
- As a user, I want to be able to remove an expense from my budget list
- As a user, I want to be able to add my budget ($) to the app
- As a user, I want ot be able to see my total expenses
- As a user, I want to be able to see how my budget relates to my expenses
- As a user, I want to be able to modify an expense in my budget
- As a user, I want to be able to save my budget list to the file
- As a user, I want to be able to load my saved budget list from the file

### Phase 4: Task 2:
Test Sample for event logger:

Fri Apr 01 16:41:14 PDT 2022
Added an expense to budget list: Netflix - $50.0.

Fri Apr 01 16:41:14 PDT 2022
Added an expense to budget list: AmazonPrime - $40.0.

Fri Apr 01 16:41:23 PDT 2022
Added an expense to budget list: BusFare - $5.0.

Fri Apr 01 16:41:27 PDT 2022
Removed an expense from budget list: AmazonPrime - $40.0.

Fri Apr 01 16:41:28 PDT 2022
Removed an expense from budget list: Netflix - $50.0.

### Phase 4: Task 3:
Overall, I think the class diagram shows that my project was a bit simple. I think I could have implemented 
more methods that would add more complexity to both the application itself and allow me to add more 
functionality to the application down the road. As it stands now, I think the applications works well as intended.
In my designing of the core Classes of the Application, Expense and BudgetList, if I could refactor the code, I would
refactor the code in a way that would let me create multiple budget lists in a single application, and would be done
by implementing a bidirectional relationship. The changes I would make to the program are outlined below:

- Add a relationship between BudgetAppGUI and Main, instead of creating a separate Main method in BudgetAppGUI class
- Make another Abstract Class for buttons and have extends and implements Classes for each button type
- Move private classes of BudgetAppGUI to separate public classes
- Add a bidirectional association between Expense and BudgetList