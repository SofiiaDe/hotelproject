<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Application form for client to book room</title>
</head>
<body>

<form method="post" action="controller?command=submitApplication">

    <h2>Choose type of room by its beds</h2>
    <p>
        <input type="radio" value="single" checked name="room_seats"/>single
    </p>
    <p>
        <input type="radio" value="double" name="room_seats"/>double
    </p>
    <p>
        <input type="radio" value="triple" name="room_seats"/>triple
    </p>
    <p>
        <input type="radio" value="twin" name="room_seats"/>twin
    </p>
    <br>
    <h2>Choose room class</h2>
    <p>
        <input type="radio" value="standard" checked name="room_class"/>standard
    </p>
    <p>
        <input type="radio" value="business" name="room_class"/>business
    </p>
    <p>
        <input type="radio" value="lux" name="room_class"/>lux
    </p>
    <br>
    <h2>Choose time of staying at the hotel</h2>

    <form>

        <p>
            <label for="date">Check-in date: </label>
            <input type="date" id="date" name="checkin_date"/>
        </p>
        <p>
            <label for="date">Check-out date: </label>
            <input type="date" id="dateout" name="checkout_date"/>
        </p>
        <br>
        <p>
            <button type="submit">Submit</button>
        </p>
    </form>
</form>
</body>
</html>
