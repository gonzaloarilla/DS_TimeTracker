import 'package:flutter/material.dart';
import 'package:time_tracker_flutter/page_intervals.dart';
import 'package:time_tracker_flutter/page_report.dart';
import 'package:time_tracker_flutter/requests.dart';
import 'package:time_tracker_flutter/tree.dart' as Tree hide getTree;
import 'dart:core';
import 'dart:convert';


class PageSearch extends StatefulWidget {
  // final int id; // final because StatefulWidget is immutable
  // PageInfo(this.id);

  @override
  _PageSearchState createState() => _PageSearchState();
}

class _PageSearchState extends State<PageSearch> {
  late int id;
  late Future<Tree.Tree> futureTree;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Project name"),
        actions: <Widget>[
          IconButton(icon: Icon(Icons.home),
              onPressed: () {
                Navigator.popUntil(context, ModalRoute.withName('/'));
              }
          ),
          IconButton(
              onPressed: () {}, icon: Icon(Icons.search))
        ],
      ),
    );
  }
}