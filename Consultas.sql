/*Consultar los permisos de un rol. En este caso, un gerente designado*/
select p.nombrePermiso as nombrePermiso from permiso as p 
inner join rol_has_permiso as rp on rp.Permiso_idPermiso = p.idPermiso
inner join rol as r on r.idRol = rp.Rol_idRol
where r.nombreRol = "Gerente Designado";

/*Consultar todos los datos de un empleado*/
select p.cedula, p.primerNombre, p.segundoNombre,p.primerApellido, p.segundoApellido, p.Direccion_idDireccion, p.fechaNacimiento, p.nacionalidad, p.genero, e.profesion, e.cargo, e.salario,e.Sede_idSede  from persona as p
inner join empleado as e on p.cedula=e.Persona_cedula
where e.Persona_cedula ="0"; 

select cal.numeroCalle,cal.letraCalle,cal.bis,cal.sur,car.numeroCarrera,car.letraCarrera,car.bis,car.este from direccion as d
inner join calle as cal on cal.idCalle = d.Calle_idCalle
inner join carrera as car on car.idCarrera = d.Carrera_idCarrera
where d.idDireccion = "1";

