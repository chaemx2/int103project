create database books;
show databases;
use books;

create table fiction(
romance varchar(50),
adventure varchar(50),
thriller varchar(50),
mystery varchar(50),
fantasy varchar(50),
horror varchar(50)
);

insert into fiction (romance, adventure, thriller, mystery, fantasy, horror)
values('Happy Place', 'The Hobbit', 'Good Bad Girl', 'A Place of Execution', 'The Fifth Season', 'It');

insert into fiction (romance, adventure, thriller, mystery, fantasy, horror)
values('A Novel', 'The Lost World', 'The Only One Left', 'The Chill', 'A Game of Thrones', 'Dracula');

insert into fiction (romance, adventure, thriller, mystery, fantasy, horror)
values('Outlander', 'Treasure Island', 'The Housemaid is Secret', 'In the Woods', 'The Name of the Wind', 'Ring' );

insert into fiction (romance, adventure, thriller, mystery, fantasy, horror)
values('The Deal', 'The Call of the Wild', 'All the Dangerous Things', 'Gone Girl', 'Assassin is Apprentice', 'The Exorcist');

select*from fiction;

create table nonfiction(
travel varchar(50),
cookbook varchar(50),
history varchar(50),
science varchar(50),
biography varchar(50),
selfhelp varchar(50)
);

insert into nonfiction (travel, cookbook, history, science, biography, selfhelp)
values('Into the Wild', 'Plenty', '1776', 'The God Delusion', 'Born a Crime', 'Outliers');

insert into nonfiction (travel, cookbook, history, science, biography, selfhelp)
values('Vagabonding', 'Japanese Farm Food', 'Walden', 'A Brief History of Time', 'When Breath Becomes Air', 'The Alchemist');

insert into nonfiction (travel, cookbook, history, science, biography, selfhelp)
values('Down Under', 'Rick Stein is Coast to Coast', 'Salt', 'Silent Spring', 'Educated', 'Thinking, Fast and Slow');

insert into nonfiction (travel, cookbook, history, science, biography, selfhelp)
values('The Geography of Bliss', 'Home Cooking', 'Night', 'Thinking, Fast and Slow', 'Becoming', 'Rich Dad Poor Dad');

insert into nonfiction (travel, cookbook, history, science, biography, selfhelp)
values('Into Thin Air', 'New Pierre Dukan', 'Hiroshima', 'Lab Girl', 'I am Glad My Mom Died', '12 Rules for Life');

insert into nonfiction (travel, cookbook, history, science, biography, selfhelp)
values('The Bone Man of Benares', 'Arabesques', 'Hidden Figures', 'On the Origin of Species', 'Yes Please', 'The Power of Habit');

select*from nonfiction;

create table other(
drama varchar(50),
poetry varchar(50),
anthology varchar(50),
children varchar(50),
graphic varchar(50),
business varchar(50)
);

insert into other(drama, poetry, anthology, children, graphic, business)
values('Waiting for Godot', 'Milk and honey', 'God Save Sex Pistols', 'Goodnight Moon', 'Watchmen', 'The Lean Startup');

insert into other(drama, poetry, anthology, children, graphic, business)
values('The Kite Runner', 'Home Body', 'A Dance with Fred Astaire', 'If He Had Been with Me', 'Batman: Year One', 'The innovator is dilemma');

insert into other(drama, poetry, anthology, children, graphic, business)
values('The Tempest', 'Leaves of Grass', 'Fragile Things', 'Coraline', 'The Amazing Spider-Man', 'Getting Things Done');

insert into other(drama, poetry, anthology, children, graphic, business)
values('To Kill a Mockingbird', 'Iliad', 'Again, Dangerous Visions', 'Gorgeously Me!', 'The Mysteries', 'The 4-Hour Workweek');

insert into other(drama, poetry, anthology, children, graphic, business)
values('A Raisin in the Sun', 'Harmony', 'The Language of Thorns', 'The Human Invasion', 'Guts', 'The Goal');

insert into other(drama, poetry, anthology, children, graphic, business)
values('The Glass Menagerie', '100 Poems That Matter', 'This Book Is a Plant', 'Charlotte is Web', 'The Hard Switch', 'The World Is Flat');

select * from other;

