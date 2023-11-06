drop database if exists jayabimahardware;

create database if not exists jayabimahardware;

use jayabimahardware;

create table user(
                     name varchar(15) not null,
                     mobile varchar(10) not null,
                     password varchar(20) not null,
                     repeatpassword varchar(20) not null
);

create table customer(
                         cus_id varchar(5) primary key,
                         cus_name varchar(20) not null,
                         cus_address varchar(30)not null,
                         cus_mobile varchar(10)not null
);

create table employee(
                         emp_id varchar(5) primary key,
                         emp_name varchar(20) not null,
                         emp_address varchar(30)not null,
                         emp_salary varchar(30)not null,
                         emp_role varchar(10)not null
);

create table Salary(
                       salary_amount varchar(20) not null,
                       salary_status varchar(30)not null,
                       emp_id varchar(5),
                       foreign key(emp_id)references Employee(emp_id)on update cascade on delete cascade
);

create table Supplier(
                         sup_id varchar(5) primary key,
                         sup_name varchar(20) not null,
                         sup_description varchar(30)not null,
                         sup_contact varchar(10)not null
);

create table Supplier_order(
    unit_price varchar(10)
);

create table Item(
                     item_id varchar(5) primary key,
                     item_name varchar(20) not null
);

create table Orders(
                       order_id varchar(5) primary key,
                       order_status varchar(20) not null,
                       order_date varchar(30)not null
);

create table SupOrder_detail(
                                unit_price double,
                                qty int
);

create table ItemOrder_detail(
                                 order_id varchar(5),
                                 item_id varchar(5),
                                 cus_id varchar(5),
                                 selling_price double,
                                 foreign key(order_id)references Orders(order_id)on update cascade on delete cascade,
                                 foreign key(item_id)references Item(item_id)on update cascade on delete cascade,
                                 foreign key(cus_id)references customer(cus_id)on update cascade on delete cascade
);


