insert into projet(projet_id, nom) values (90, 'todo-list');
insert into projet(projet_id, nom) values (91, 'tuto');


insert into klass(klass_id,nom,projet_id) VALUES (40, 'todo', 90);
insert into klass(klass_id, nom, projet_id) values (41, 'tache', 90 );
insert into klass(klass_id, nom, projet_id) values (42, 'klass_1' , 91);
insert into klass(klass_id, nom, projet_id) values (43, 'klass_2' , 91);
insert into responsabilite(responsabilite_id, titre, klass_id) values (50, 'afficher une liste de tâches actionnables', 40);
insert into responsabilite(responsabilite_id, titre, klass_id) values(51, 'décrire une étape d un objectif',41);
insert into responsabilite(responsabilite_id, titre, klass_id) values (52, 'responsabilite 1', 42);
insert into responsabilite(responsabilite_id, titre, klass_id) values (53, 'responsabilite 2', 42);
insert into collaborateur(collaborateur_id, principal_id, collaborant_id) values (60, 40,41);
insert into collaborateur(collaborateur_id, principal_id, collaborant_id) values(61, 42, 43);
