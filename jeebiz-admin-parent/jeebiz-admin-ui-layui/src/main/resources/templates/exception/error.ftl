<html>
	<head>
		<title>Error Page</title>
	</head>
	<body>
		<div id="content">
				<div>
					<h3>
						系统运行异常:<br>
					</h3>
				</div>
				<div>
					<button onclick="history.back();">Back</button>
				</div>
				<div><a href="#" onclick="$('detail_error_msg').toggle();">Administrator click here to get the detail.</a></div>
				<div id="detail_error_msg" style="display: none">
				</div>
			</div>
	</body>
</html>