package invertor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XMLInvertor {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Usage: java XMLDocumentInversion <input_xml_file>");
			System.exit(1);
		}

		String xmlFile = args[0];

		try {

			XMLInvertor invertor = new XMLInvertor(xmlFile);

			// Invert the XML document
			Stack<String> stack = invertor.traverseElements(invertor.xmlDoc.getDocumentElement(), new Stack<String>());
			invertor.print(stack, "");

		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	// XML Document
	Document xmlDoc;

	// reading XML document
	public XMLInvertor(String xmlFilePath) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		xmlDoc = builder.parse(new File(xmlFilePath));
		xmlDoc.getDocumentElement().normalize();
	}

	// for parsing the XML element and storing them in a stack
	Stack<String> traverseElements(Node parent, Stack<String> stack) {
		// Invert the parent-child relationships recursively
		if (parent != null && parent.getNodeType() == Node.ELEMENT_NODE) {
			stack.push(parent.getNodeName());
			if (parent.hasChildNodes()) {
				short nodeType = parent.getFirstChild().getNodeType();
				while (nodeType != Node.ELEMENT_NODE) {
					parent.removeChild(parent.getFirstChild());
					
					if (parent.hasChildNodes()) {
						nodeType = parent.getFirstChild().getNodeType();
					} else {
						break;
					}
				}

				Node child = parent.getFirstChild();
				if(parent.hasChildNodes())
				traverseElements(child, stack);

			}
		}
		return stack;
	}
	
	// for printing xml tags in reverse order
	void print(Stack<String> tags, String indent) {
		
		if(tags.size() == 1) {
			System.out.println(indent + "<" + tags.pop() +"/>");
		} else {
			String tag = tags.pop();
			System.out.println(indent + "<" + tag + ">");
			print(tags, indent + "    ");
			System.out.println(indent + "<" + tag + "/>");
		}
	}

}
