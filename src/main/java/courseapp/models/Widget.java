package courseapp.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

enum ListType {
	ordered, unordered, nil
}

enum WidgetType {
	Heading,Paragraph,List,Link,Image
}


@Entity
public class Widget {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private int widgetOrder;
	private String text;
	private String style;
	private String width;
	public WidgetType getWidgetType() {
		return widgetType;
	}
	public void setWidgetType(WidgetType widgetType) {
		this.widgetType = widgetType;
	}
	private String height;
	private int size;
	private String  href;
	private String src;
	private String listItems;
	@Enumerated(EnumType.STRING)
	private ListType listType;
	
	@Enumerated(EnumType.STRING)
	private WidgetType widgetType;
	
	@ManyToOne
	@JsonIgnore
	private Lesson lesson;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getWidgetOrder() {
		return widgetOrder;
	}
	public void setWidgetOrder(int widgetOrder) {
		this.widgetOrder = widgetOrder;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getListItems() {
		return listItems;
	}
	public void setListItems(String listItems) {
		this.listItems = listItems;
	}
	public ListType getListType() {
		return listType;
	}
	public void setListType(ListType listType) {
		this.listType = listType;
	}
	public Lesson getLesson() {
		return lesson;
	}
	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}
}
