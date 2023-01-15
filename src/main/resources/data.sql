insert into projet (projet_id, nom) values (10, 'projet existant');
insert into projet(projet_id, nom) values (90, 'projet test 90');

insert into klass(klass_id,nom,projet_id) VALUES (40, 'klass test 40', 90);
insert into klass(klass_id, nom, projet_id) values (41, 'klass test 41', 90 )
insert into responsabilite(responsabilite_id, titre, klass_id) values (50, 'responsabilite test 50', 40)
insert into collaborateur(collaborateur_id, principal_id, collaborant_id) values (60, 40,41);
