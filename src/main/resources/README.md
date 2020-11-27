# Project-1: Employee Reimbursment System (ERS)

## Executive Summary
* The Expense Reimbursement System (ERS) will manage the process of reimbursing employees for expenses incurred while on company time. 
* All employees in the company can login and submit requests for reimbursement and view their past tickets and pending requests. 
* Finance managers can log in and view all reimbursement requests and past history for all employees in the company. 
* Finance managers are authorized to approve and deny requests for expense reimbursement.

#### Employee User Stories 
- An Employee can login **DONE**
- An Employee can view the Employee Homepage **DONE**
- An Employee can logout **DONE**
- An Employee can submit a reimbursement request **DONE**
- An Employee can upload an image of his/her receipt as part of the reimbursement request (extra credit) **OPTIONAL**
- An Employee can view their pending reimbursement requests **DONE**
- An Employee can view their resolved reimbursement requests **DONE**
- An Employee can view their information **DONE**
- An Employee can update their information **DONE**
- An Employee receives an email when one of their reimbursement requests is resolved (optional) **OPTIONAL**

#### Manager User Stories
- A Manager can login **DONE**
- A Manager can view the Manager Homepage **DONE**
- A Manager can logout **DONE**
- A Manager can approve/deny pending reimbursement requests **DONE**
- A Manager can view all pending requests from all employees **DONE**
- A Manager can view images of the receipts from reimbursement requests (extra credit) **OPTIONAL**
- A Manager can view all resolved requests from all employees and see which manager resolved it **DONE**
- A Manager can view all Employees **DONE**
- A Manager can view reimbursement requests from a single Employee **DONE**


**State-chart Diagram (Reimbursement Statuses)** 
![](./imgs/state-chart.jpg)

**Reimbursement Types**

Employees must select the type of reimbursement as: LODGING, TRAVEL, FOOD, or OTHER.

**Logical Model**
![](./imgs/logical.jpg)

**Physical Model**
![](./imgs/physical.jpg)

**Use Case Diagram**
![](./imgs/use-case.jpg)

**Activity Diagram**
![](./imgs/activity.jpg)

## Technical Requirements

* The back-end system shall use **Hibernate** to connect to an **AWS RDS Postgres database**. **DONE**
* The application shall deploy onto a Tomcat Server. **DONE**
* The middle tier shall use Servlet technology for dynamic Web application development. **DONE**
* The front-end view shall use HTML/JavaScript to make an application that can call server-side components. **DONE**
* Passwords shall be encrypted in Java and securely stored in the database. **DONE** 
* The middle tier shall follow proper layered architecture, **DONE**
* have reasonable (~70%) test coverage of the service layer, and 
* implement log4j for appropriate logging. **DONE**

**Stretch Goals:** *These will count for extra credit and are entirely optional*
* Replace HTML/JavaScript with an Angular single page application. (We will learn Angular in Week 5)
* Users can upload a document or image of their receipt when submitting reimbursements which can stored in the database and reviewed by a financial manager.
* Application shall be hosted remotely on an EC2.
* Static files (webpages) shall be hosted on an S3 bucket. 
