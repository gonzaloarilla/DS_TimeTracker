import 'package:flutter/material.dart';
import 'package:time_tracker_flutter/page_intervals.dart';
import 'package:time_tracker_flutter/page_report.dart';
import 'package:time_tracker_flutter/tree.dart' hide getTree;
import 'package:time_tracker_flutter/requests.dart';

class PageCreateProject extends StatefulWidget {
  late final int id;


  @override
  _CreateProjectState createState() => _CreateProjectState();
}

class _CreateProjectState extends State<PageCreateProject> {
  GlobalKey formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Scaffold(
      appBar: AppBar(
        title: Text("New Project"),
        actions: <Widget>[
          IconButton(icon: Icon(Icons.home),
              onPressed: () {
                Navigator.popUntil(context, ModalRoute.withName('/'));
              }
          ),
          IconButton(
              onPressed: () {
              }, icon: Icon(Icons.search))
        ],
      ),
      body: Padding(
        padding: const EdgeInsets.all(12.0),
        child: Form(
          key: formKey,
          child: Column(
            children: <Widget>[
              TextFormField(
                decoration: InputDecoration(
                    labelText: "Name: "
                ),
              ),
              TextFormField(
                decoration: InputDecoration(
                    labelText: "Tags: "
                ),
              ),
              TextFormField(
                decoration: InputDecoration(
                    labelText: "Description: "
                ),
              ),

              OutlinedButton(
                child: Text('Create',
                  style: TextStyle(fontSize: 20),
                ),
                onPressed: (){
                  Text("Project Created!");
                },
                style: OutlinedButton.styleFrom(
                    side: BorderSide(
                      width: 2.0,
                      color: Colors.blue,
                      style: BorderStyle.solid,
                    ),

                ),
              )
            ],
          ),
        ),
      ),
    );
  }



}

