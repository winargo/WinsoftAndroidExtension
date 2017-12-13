CREATE FUNCTION DBO.GETPASS
( @PASS VARCHAR(50) )
RETURNS VARCHAR(25) 
AS 
BEGIN
	DECLARE @HIT INT, @MKEMBALI VARCHAR(25), @MAWAL VARCHAR(25)
	SET @HIT=1
	SET @MKEMBALI=''
	SET @MAWAL=''
	WHILE @HIT<=LEN(LTRIM(RTRIM(@PASS)))
	BEGIN
		SET @MKEMBALI=SUBSTRING(@PASS,@HIT,3)
		SET @MAWAL=@MAWAL+CHAR((CAST(@MKEMBALI AS MONEY)+11)/3-5)
		SET @HIT=@HIT+3
	END
	RETURN (@MAWAL)
END