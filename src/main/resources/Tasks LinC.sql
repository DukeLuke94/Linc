INSERT INTO circle (circle_id, circle_name)
VALUES (-1, 'Oom Vincent'), (-2, 'Tante Joanne'), (-3, 'Opa Tinus'), (-4, 'Buurvrouw Jannie');

# INSERT INTO task (task_id, task_description, task_name, circle_id)
# VALUES 	(-5, 'Iedere dag de avond van te voren om 20:00 uur checken of er voor morgen iets belangrijks op het programma staat en zo bepalen hoelaat het tijd is om naar bed te gaan', 'Helpen op tijd te komen', -1),
#           (-6, '-',false, 'Afspraak maken bij KRIBB', -1),
#           (-7, '-', 'IntelliJ van Gerald fixen', -1),
#           (-8, '-', 'Katten voeren', -2),
#           (-9, '-', 'Rummikub spelen in het weekend', -2),
#           (-10, '-', 'Gras maaien achtertuin', -2),
#           (-11, '-', 'Auto APK afspraak maken', -3),
#           (-12, '-', 'Ramen wassen', -3),
#           (-16, '-', 'Uitje zwarte markt', -3),
#           (-13, '-', 'Krant langsbrengen', -4),
#           (-14, '-', 'Planten water geven', -4),
#           (-15, '-', 'Container bij de weg zetten', -4);



INSERT INTO task (task_id, task_description,task_done, task_name, circle_id)
VALUES 	(-5, 'Iedere dag de avond van te voren om 20:00 uur checken of er voor morgen iets belangrijks op het programma staat en zo bepalen hoelaat het tijd is om naar bed te gaan',false, 'Helpen op tijd te komen', -1),
          (-6, 'Tijd voor de zomerlook',false, 'Afspraak maken bij KRIBB', -1),
          (-7, 'Zal ook weer eens niet',false, 'IntelliJ van Gerald fixen', -1),
          (-8, 'Niet stiekem zelf brokjes eten',false, 'Katten voeren', -2),
          (-9, 'We moeten haar wel laten winnen',false, 'Rummikub spelen in het weekend', -2),
          (-10, 'Maar niet maaien in mei',false, 'Gras maaien achtertuin', -2),
          (-11, 'Niet bij Jet Cars Rotterdam!',false, 'Auto APK afspraak maken', -3),
          (-12, 'Ook die op zolder!',false, 'Ramen wassen', -3),
          (-16, 'Go Beverwijk!',false, 'Uitje zwarte markt', -3),
          (-13, 'De telegraaf kan in de prullenbak',false, 'Krant langsbrengen', -4),
          (-14, 'niet te veel water!',false, 'Planten water geven', -4),
          (-15, 'De grijze container',false, 'Container bij de weg zetten', -4);