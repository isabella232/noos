package me.noos.model
{
	import com.adobe.serialization.json.JSON;
	
	import mx.collections.ArrayCollection;
	import mx.collections.Sort;
	import mx.containers.VBox;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.http.HTTPService;
	
	import spark.components.Label;

	public class Model
	{
		private static var _instance:Model;
		
		[Bindable]
		public var currentUser:String;
		
		public var actionBar:VBox;
		
		public function Model(lock:Class)
		{
			if (lock != SingletonLock)
			{
				throw new Error("Invalid Singleton access.  Use Model.instance.");
			}
		}
		
		public static function get instance():Model
		{
			if (_instance == null)
			{
				_instance = new Model(SingletonLock);
			}
			
			return _instance;
		}
	}
}

class SingletonLock {}