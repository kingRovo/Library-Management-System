# Library Management System 

Focus on the following set of requirements while designing the Library Management System: 

  

* Any library member should be able to search books by their title, author, subject category as well by the publication date. 

* Each book will have a unique identification number and other details including a rack number which will help to physically locate the book. 

* There could be more than one copy of a book, and library members should be able to check-out and reserve any copy. We will call each copy of a book, a book item. 

* The system should be able to retrieve information like who took a particular book or what are the books checked-out by a specific library member. 

* There should be a maximum limit (5) on how many books a member can check-out. 

* There should be a maximum limit (10) on how many days a member can keep a book. 

* The system should be able to collect fines for books returned after the due date. 10 Rs fine per book per day. 

* Members should be able to reserve books that are currently available. 

* The system should be able to send notifications whenever the reserved books become available, as well as when the book is not returned within the due date. 

* Each book and member card will have a unique barcode. The system will be able to read barcodes from books and members’ library cards. 

 
 

## Three main actors in our system: 

* Librarian: Mainly responsible for adding and modifying books, book items, and users. The Librarian can also issue, reserve, and return book items. 

* Member: All members can search the catalogue, as well as check-out, reserve, renew, and return a book. 

* System: Mainly responsible for sending notifications for overdue books. 


