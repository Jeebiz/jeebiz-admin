<!--jQuery.validation校验插件 -->
<link rel="stylesheet" type="text/css" href="/webjars/zftal-ui-v5/plugins/validation/css/jquery.validate-min.css?ver=${versionUtil()}" />
<script type="text/javascript" src="/webjars/zftal-ui-v5/plugins/validation/js/jquery.validate-min.js?ver=${versionUtil()}" charset="utf-8"></script>
<script type="text/javascript" src="/webjars/zftal-ui-v5/plugins/validation/js/jquery.validate.contact-min.js?ver=${versionUtil()}" charset="utf-8"></script>
<script type="text/javascript" src="/webjars/zftal-ui-v5/plugins/validation/js/jquery.validate.methods.contact-min.js?ver=${versionUtil()}" charset="utf-8"></script>
<script type="text/javascript" src="/webjars/zftal-ui-v5/plugins/validation/lang/zh_CN-min.js?ver=${versionUtil()}" charset="utf-8"></script>
<script type="text/javascript">
	jQuery(function($){
		$('[data-toggle*="validation"]').trigger("validation");
	});
</script>