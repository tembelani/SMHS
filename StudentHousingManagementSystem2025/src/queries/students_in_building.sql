
-- view students in the building

SELECT s.student_id, s.first_name, s.last_name, s.email, s.course, r.room_number
FROM Students s
JOIN Contracts c ON s.student_id = c.student_id
JOIN Rooms r ON c.room_id = r.room_id
JOIN Buildings b ON r.building_id = b.building_id
WHERE b.building_name = 'Campus Towers'
AND c.status = 'active';