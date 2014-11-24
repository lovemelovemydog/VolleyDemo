VolleyDemo
==========

android google volley 访问网络框架

just like 
private void getStringVolley() {
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		StringRequest sRequest=new StringRequest(Request.Method.GET,CITY_CODE_URL, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				System.out.println(response);				
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError arg0) {
				System.out.println("sorry,Error");
			}
		});
		sRequest.setShouldCache(false);
		requestQueue.add(sRequest);
	}
	/**
	 * 利用Volley获取JSON数据
	 */
	private void getJSONByVolley() {
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		final ProgressDialog progressDialog = ProgressDialog.show(this,
				"This is title", "...Loading...");
		
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				Request.Method.GET, CITY_CODE_URL, null,
				new Response.Listener<JSONObject>() {
					@Override 
					public void onResponse(JSONObject response) {
						System.out.println("response=" + response);
						tv_1.setText(response.toString());
						if (progressDialog.isShowing()
								&& progressDialog != null) {
							progressDialog.dismiss();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						tv_1.setText(arg0.toString());
						System.out.println("sorry,Error");
						if (progressDialog.isShowing()
								&& progressDialog != null) {
							progressDialog.dismiss();
						}
					}
				});
		requestQueue.add(jsonObjectRequest);
	}

volley
