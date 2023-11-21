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

create table Employee(
    emp_id varchar(5) primary key,
    emp_name varchar(20) not null,
    emp_role varchar(10)not null,
    emp_address varchar(30)not null,
    emp_salary varchar(30)not null,
    emp_mobile varchar(10)not null

);

create table Salary(
    emp_id varchar(5),
    salary_amount varchar(20) not null,
    salary_status varchar(30),
    foreign key(emp_id)references Employee(emp_id)on update cascade on delete cascade
);

create table Supplier(
    sup_id varchar(5) primary key,
    sup_name varchar(20) not null,
    sup_description varchar(30)not null,
    sup_contact varchar(10)not null
);


create table Item(
    item_id varchar(5) primary key,
    item_name varchar(20) not null,
    item_desc varchar(50) not null,
    item_qty int not null,
    item_unitPrice double,
    sup_id varchar(5),
    foreign key (sup_id) references supplier(sup_id)on update cascade on delete cascade
);


create table Orders(
    order_id varchar(5) primary key,
    cus_id varchar(5),
    order_totalPrice double,
    order_date varchar(30)not null,
    foreign key (cus_id) references customer(cus_id) on update cascade on DELETE cascade

);

create table Stock_order(
    stockOrder_id varchar(5) primary key ,
    sup_id varchar(5),
    supOrder_date date,
    foreign key (sup_id) references supplier (sup_id)on UPDATE cascade on DELETE cascade

);

create table StockOrder_detail(
    stockOrder_id varchar(5),
    item_id varchar(5),
    qty int,
    foreign key (item_id) references item(item_id) on update cascade on delete cascade,
    foreign key (stockOrder_id) references stock_order(stockOrder_id)on update cascade on delete cascade

);

create table ItemOrder_detail(
    order_id varchar(5),
    item_id varchar(5),
    selling_price double,
    foreign key(order_id)references Orders(order_id)on update cascade on delete cascade,
    foreign key(item_id)references Item(item_id)on update cascade on delete cascade
);


