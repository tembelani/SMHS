-- available rooms

SELECT b.building_name, r.room_number, r.capacity, r.monthly_rent
FROM Rooms r
JOIN Buildings b ON r.building_id = b.building_id
WHERE r.is_available = TRUE
ORDER BY b.building_name, r.room_number;