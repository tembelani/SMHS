-- waitlist students

SELECT s.student_id, s.first_name, s.last_name, s.email, s.course, s.max_budget
FROM Students s
WHERE s.student_id NOT IN (
    SELECT DISTINCT student_id 
    FROM Contracts 
    WHERE status = 'active'
)
ORDER BY s.created_at;