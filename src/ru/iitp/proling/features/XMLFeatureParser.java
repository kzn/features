package ru.iitp.proling.features;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;


public class XMLFeatureParser {
	Evaluator eval = new Evaluator();
	FeatureBuilder fb;
	
	public XMLFeatureParser(FeatureBuilder fb) {
		this.fb = fb;
	}
	
	public void parse(String fileName) throws XMLStreamException, IOException {
		XMLInputFactory f = XMLInputFactory.newFactory();
		XMLStreamReader reader = null;
		try {
			reader = f.createXMLStreamReader(new FileInputStream(fileName));
			
			while(reader.hasNext()) {
				int e = reader.next();
				
				if(e == XMLEvent.START_ELEMENT && reader.getLocalName().equals("feature"))
					eval.addFeature(parseFeature(reader));
			}
			
		} finally {
			reader.close();
		}
	}

	protected FeatureValue parseFeature(XMLStreamReader reader) throws XMLStreamException {
		String name = "";
		List<Value> args = new ArrayList<Value>();

		while(reader.hasNext()) {
			int event = reader.next();
			if(event == XMLEvent.START_ELEMENT) { 
				if(reader.getLocalName().equals("name")) {
					name = reader.getElementText();
					if(fb.hasInjectedArg(name))
						args.add(eval.init);
				} else if(reader.getLocalName().equals("arg")) {
					args.add(parseArg(reader));
				}
			} 
			
			if(event == XMLEvent.END_ELEMENT && reader.getLocalName().equals("feature"))
				break;
		}
		
		if(name.isEmpty())
			return null;
		
		Feature f = fb.build(name, args);
		FeatureValue fv = new FeatureValue(f, new SimpleValue());
		Value fv0 = eval.get(fv);
		
		if(fv == fv0) {
			for(Value arg : args)
				eval.setArg(fv, arg);
		}
		
		return (FeatureValue) fv0;
	}

	private Value parseArg(XMLStreamReader reader) throws XMLStreamException {
		Value v = null;
		
		while(reader.hasNext()) {
			int event = reader.next();
			
			if(event == XMLEvent.START_ELEMENT) {
				if(reader.getLocalName().equals("feature")) {
					v = parseFeature(reader);
				} else if(reader.getLocalName().equals("int")) {
					v = new SimpleValue(Integer.parseInt(reader.getElementText()));
				} else if(reader.getLocalName().equals("string")) {
					v = new SimpleValue(reader.getElementText());
				}
			}
			
			if(event == XMLEvent.END_ELEMENT && reader.getLocalName().equals("arg"))
				break;
				
		}
		
		return v;
	}
	
	public Evaluator getEval() {
		return eval;
	}	
}
