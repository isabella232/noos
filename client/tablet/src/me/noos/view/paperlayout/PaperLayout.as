package me.noos.view.paperlayout
{
	import flash.display.DisplayObjectContainer;
	
	import flashx.textLayout.conversion.TextConverter;
	import flashx.textLayout.elements.Configuration;
	import flashx.textLayout.elements.TextFlow;
	import flashx.textLayout.formats.TextAlign;
	import flashx.textLayout.formats.TextLayoutFormat;
	
	import mx.collections.ArrayCollection;
	import mx.core.UIComponent;
	
	import spark.components.Group;
	
	public class PaperLayout extends Group
	{
		private var configuration:Configuration;
		
		public function PaperLayout()
		{
//			var layoutState:State = new State();
//			layoutState.name = "layout";
//			var focusState:State = new State();
//			focusState.name = "focus";
//			states.push(layoutState);
//			states.push(focusState);
		}
		
		protected function addStory(uiComp:Group, storyXml:XML):void
		{
			var container:UIComponent = new UIComponent();
			var story:PaginationWidget = new PaginationWidget();
			uiComp.addElement(container);
			container.addChild(story);
			story.setSize(uiComp.width, uiComp.height);
			
			// Configuration passed to any TextFlows the default importer is importing
			configuration = TextFlow.defaultConfiguration;
			configuration.inactiveSelectionFormat = configuration.focusedSelectionFormat;
			configuration.unfocusedSelectionFormat  = configuration.focusedSelectionFormat;
			
			var textFlow:TextFlow = TextConverter.importToFlow(storyXml, TextConverter.TEXT_LAYOUT_FORMAT, configuration);
			
			var ca:TextLayoutFormat = new TextLayoutFormat(textFlow.format);
			ca.fontFamily = "Georgia, Times";
			ca.fontSize = 16;
			ca.textIndent = 15;
			ca.paragraphSpaceAfter = 10;
			ca.textAlign = TextAlign.JUSTIFY;
			textFlow.format = ca;
			
			story.textFlow = textFlow;
		}
		
		protected function storyToTextFlow(story:Object):XML
		{
			return <TextFlow xmlns='http://ns.adobe.com/textLayout/2008'><p textAlign='center' fontSize='24'>{story.title}</p>{story.description}</TextFlow>;
		}
		
		public function setStories(stories:ArrayCollection):void
		{
			trace("SHOULD BE OVERRIDDEN AND EXECUTED IN CHILD");
		}
	}
}