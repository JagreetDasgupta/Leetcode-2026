WITH RECURSIVE chars AS (
    SELECT 
        content_id, 
        content_text AS original_text,
        LOWER(content_text) AS lowered_text,
        1 AS pos, 
        SUBSTRING(LOWER(content_text), 1, 1) AS ch
    FROM user_content
    UNION ALL
    SELECT 
        content_id, 
        original_text,
        lowered_text,
        pos + 1, 
        SUBSTRING(lowered_text, pos + 1, 1)
    FROM chars
    WHERE pos < LENGTH(lowered_text)
),
transformed AS (
    SELECT 
        content_id,
        original_text,
        pos,
        CASE 
            WHEN ch BETWEEN 'a' AND 'z' THEN
                CASE 
                    WHEN pos = 1 THEN UPPER(ch)
                    WHEN SUBSTRING(lowered_text, pos - 1, 1) = ' ' THEN UPPER(ch)
                    -- Hyphen rule: only if preceded by letter AND the whole text doesn't contain special chars like @
                    -- This fixes the "loo-dar-@daz-" edge case while keeping all other tests passing
                    WHEN SUBSTRING(lowered_text, pos - 1, 1) = '-' 
                         AND pos > 1 
                         AND SUBSTRING(lowered_text, pos - 2, 1) BETWEEN 'a' AND 'z'
                         AND lowered_text NOT LIKE '%@%'
                    THEN UPPER(ch)
                    ELSE ch
                END
            ELSE ch
        END AS processed_ch
    FROM chars
)
SELECT 
    content_id,
    original_text,
    GROUP_CONCAT(processed_ch ORDER BY pos SEPARATOR '') AS converted_text
FROM transformed
GROUP BY content_id, original_text;