create trigger TR_01 
after insert ON Empleado for each row
insert into auditoriaEmpleados (fechaAuditoria,Persona_cedula,Sede_idSede,profesion,cargo,salario) 
values ((select now()), new.Persona_cedula,new.Sede_idSede,new.profesion,new.cargo,new.salario);


create table auditoriaEmpleados
(
fechaAuditoria datetime,
Persona_cedula int,
Sede_idSede int,
profesion varchar(20),
cargo varchar(20),
salario double
);

create trigger TR_02 
after insert ON FacturaVenta for each row
insert into auditoriaFacturas (fechaInsercionFactura,id_fventa,Empleado_Persona_cedula,Cliente_Persona_cedula,iva,totalVenta,fecha) 
values ((select now()),new.id_fventa,new.Empleado_Persona_cedula,new.Cliente_Persona_cedula,new.iva,new.totalVenta,new.fecha);

create table auditoriaFacturas
(
fechaInsercionFactura datetime,
id_fventa int,
Empleado_Persona_cedula int,
Cliente_Persona_cedula int,
iva double,
totalVenta double,
fecha date
);

select * from auditoriaFacturas;





