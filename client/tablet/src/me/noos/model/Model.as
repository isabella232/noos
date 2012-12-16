package me.noos.model
{
	import spark.components.Group;

	public class Model
	{
		private static var _instance:Model;
		
		[Bindable]
		public var currentUser:String;
		
		public var actionBar:Group;
		
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