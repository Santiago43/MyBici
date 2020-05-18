/*Consultar los permisos de un rol. En este caso, un gerente designado*/
select p.nombrePermiso as nombrePermiso from permiso as p 
inner join rol_has_permiso as rp on rp.Permiso_idPermiso = p.idPermiso
inner join rol as r on r.idRol = rp.Rol_idRol
where r.nombreRol = "Gerente Designado";


