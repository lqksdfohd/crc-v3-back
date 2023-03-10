insert into projet(projet_id, nom) values (90, 'todo-list');

insert into klass(klass_id,nom,projet_id) VALUES (40, 'todo', 90);
insert into klass(klass_id, nom, projet_id) values (41, 'tache', 90 );
insert into responsabilite(responsabilite_id, titre, klass_id) values (50, 'afficher une liste de tâches actionnables', 40);
insert into responsabilite(responsabilite_id, titre, klass_id) values(51, 'décrire une étape d un objectif',41);
insert into collaborateur(collaborateur_id, principal_id, collaborant_id) values (60, 40,41);
