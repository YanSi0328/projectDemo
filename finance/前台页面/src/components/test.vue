<template>
	<el-transfer v-model="value" :data="data"></el-transfer>
	
	<!-- {
        "170001": "PE香港平衡基金【基金】",
        "170002": "测试量化明星产品A【基金】",
        "170003": "全球量化私募对冲【基金】",
        "170004": "专项股权基金(ML)【基金】",
        "170005": "私募股权投资【基金】",
        "170006": "中金收益宝【基金】",
        "170007": "测试基金产品【基金】",
        "170008": "中金收益宝(基金)",
        "170009": "中金收益宝【基金】",
        "170010": "中金收益宝【基金】"
    } -->
</template>

<script>
	export default {
		data() {
			const generateData = _ => {
				const data = [];
				for (let i = 1; i <= 15; i++) {
					data.push({
						key: i,
						label: `备选项 ${ i }`
					});
				}
				return data;
			};
			return {
				data: generateData(),
				value: [1,2, 4]
			};
		}
	};
</script>
