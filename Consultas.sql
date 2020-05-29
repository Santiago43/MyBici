/*Consultar los permisos de un rol. En este caso, un gerente designado*/
select p.nombrePermiso as nombrePermiso from permiso as p 
inner join rol_has_permiso as rp on rp.Permiso_idPermiso = p.idPermiso
inner join rol as r on r.idRol = rp.Rol_idRol
where r.nombreRol = "Gerente Designado";

/*Consultar los permisos de un usuario*/

select p.* from permiso as p 
inner join usuario_has_permiso as up on up.Permiso_idPermiso = p.idPermiso
inner join usuario as u on u.usuario = up.Usuario_usuario
where u.usuario = "admin";
/*Consultar todos los datos de un empleado*/
select p.cedula, p.primerNombre, p.segundoNombre,p.primerApellido, p.segundoApellido, p.Direccion_idDireccion, p.fechaNacimiento, p.nacionalidad, p.genero, e.profesion, e.cargo, e.salario,e.Sede_idSede  from persona as p
inner join empleado as e on p.cedula=e.Persona_cedula; 

select cal.numeroCalle,cal.letraCalle,cal.bis as bisCalle,cal.sur,car.numeroCarrera,car.letraCarrera,car.bis as bisCarrera,car.este from direccion as d
inner join calle as cal on cal.idCalle = d.Calle_idCalle
inner join carrera as car on car.idCarrera = d.Carrera_idCarrera
where d.idDireccion=7;


/*Consultar una dirección a partir de un id*/
select d.idDireccion, d.Calle_idCalle,d.Carrera_idCarrera, cal.idCalle, cal.numeroCalle, cal.letraCalle, cal.bis,cal.sur, car.idCarrera,car.numeroCarrera,car.letraCarrera,car.bis,car.este from direccion as d 
inner join calle as cal on cal.idCalle = d.Calle_idCalle
inner join carrera as car on car.idCarrera = d.Carrera_idCarrera
where d.idDireccion="1";

/*Consultar clientes*/
select p.cedula, p.primerNombre, p.segundoNombre,p.primerApellido, p.segundoApellido, p.Direccion_idDireccion, p.fechaNacimiento, p.nacionalidad, p.genero from persona as p
inner join cliente as c on p.cedula=c.Persona_cedula
where c.Persona_cedula ="1000853624"; 

/*Consultar permisos (general)*/

select * from permiso where idPermiso =1;

/*Consultar todos los datos de la sede*/
select * from sede where idSede =1;
select * from inventario where Sede_idSede=3;

select * from objeto as o 
inner join mercancia as m on o.idObjeto = m.Objeto_idObjeto
where m.Inventario_id_inventario=1;



select * from objeto as o 
inner join equipooficina as e on o.idObjeto = e.Objeto_idObjeto
where e.Sede_idSede = 3;


select p.* from proveedor as p
inner join objeto_has_proveedor as op on op.Proveedor_idProveedor = p.idProveedor
inner join objeto as o on o.idObjeto = op.Objeto_idObjeto
where o.idObjeto = 1;

select * from objeto;




/*Seleccionar todos los datos de un proveedor*/
select * from proveedor where idProveedor = 1;

select * from proveedor_has_telefono;


/*Seleccionar todos los telefonos de un proveedor*/
select t.id_telefono,t.tipo,pt.numeroTelefono from telefono as t
inner join proveedor_has_telefono as pt on pt.Telefono_id_telefono = t.id_telefono
inner join proveedor as p on p.idProveedor=pt.Proveedor_idProveedor
where p.idProveedor =1;


/*Seleccionar todo el equipo de oficina de una sede*/
select o.*,eo.*,p.* from equipoOficina as eo
inner join sede as s on s.idSede = eo.Sede_idSede
inner join objeto as o on o.idObjeto=eo.Objeto_idObjeto
inner join objeto_has_proveedor as op on o.idObjeto=op.Objeto_idObjeto
inner join proveedor as p on p.idProveedor=op.Proveedor_idProveedor
where s.idSede = 1;

select * from rol;

update rol set nombreCorto = "MecJ" where nombreRol= "Jefe mecánico";



select cal.idCalle,cal.numeroCalle,cal.letraCalle,cal.bis as bisCalle,cal.sur,car.idCarrera,car.numeroCarrera,car.letraCarrera,car.bis as bisCarrera,car.este from direccion as d
inner join calle as cal on cal.idCalle = d.Calle_idCalle
inner join carrera as car on car.idCarrera = d.Carrera_idCarrera
where d.idDireccion = 1;

/**
*/
select * from valores_financieros;
/*Mantenimientos*/
select * from mantenientobicicleta;
/*Facturas*/
select * from facturaventa;
/*Bicicletas*/
select * from bicicleta;


/*Consultar facturas entre fechas de una sede*/
select v.* from facturaventa as v
inner join empleado as e on v.Empleado_Persona_cedula=e.Persona_cedula
inner join Sede as s on e.Sede_idSede=s.idSede
where s.idSede = 1 and v.fecha between "2020-05-01" and "2020-05-31";

/*Nomina*/
select * from nomina;
/*Consultar peticiones por id*/
select * from peticionempleado where idPeticionEmpleado =1;


/*Consultas de proveedores*/

select pro.*, sum(eo.valorAdquisicion) as total,count(pro.idProveedor) as cantidad from proveedor as pro
inner join objeto_has_proveedor as op on pro.idProveedor = op.Proveedor_idProveedor
inner join objeto as o on op.Objeto_idObjeto = o.idObjeto
inner join equipooficina as eo on eo.Objeto_idObjeto = o.idObjeto
order by total desc;

select * from taller where Sede_idSede =1;

select * from mantenimientoTaller where Taller_idTaller = 1;


