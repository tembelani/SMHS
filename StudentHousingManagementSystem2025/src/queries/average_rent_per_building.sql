-- average rent per building

SELECT b.building_name, 
       COUNT(r.room_id) as total_rooms,
       AVG(r.monthly_rent) as average_rent,
       MIN(r.monthly_rent) as min_rent,
       MAX(r.monthly_rent) as max_rent
FROM Buildings b
JOIN Rooms r ON b.building_id = r.building_id
GROUP BY b.building_id, b.building_name
ORDER BY average_rent DESC;